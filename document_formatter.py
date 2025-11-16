"""
Document Formatter
Converts Google Docs content to various formats while preserving formatting.
"""

from typing import Dict, List, Any


class DocumentFormatter:
    """Formats Google Docs content to HTML, Markdown, or plain text."""

    def __init__(self, document: Dict[str, Any]):
        """
        Initialize the formatter with a Google Docs document.

        Args:
            document: Google Docs document dictionary from API
        """
        self.document = document
        self.content = document.get('body', {}).get('content', [])

    def to_html(self) -> str:
        """
        Convert document to HTML format.

        Returns:
            HTML string with preserved formatting
        """
        html_parts = ['<!DOCTYPE html>', '<html>', '<head>']
        html_parts.append('<meta charset="UTF-8">')
        html_parts.append('<style>')
        html_parts.append('body { font-family: Arial, sans-serif; max-width: 800px; margin: 0 auto; padding: 20px; }')
        html_parts.append('h1 { font-size: 2em; margin-top: 0.67em; margin-bottom: 0.67em; }')
        html_parts.append('h2 { font-size: 1.5em; margin-top: 0.83em; margin-bottom: 0.83em; }')
        html_parts.append('h3 { font-size: 1.17em; margin-top: 1em; margin-bottom: 1em; }')
        html_parts.append('p { margin: 1em 0; }')
        html_parts.append('ul, ol { margin: 1em 0; padding-left: 40px; }')
        html_parts.append('</style>')
        html_parts.append('</head>')
        html_parts.append('<body>')

        for element in self.content:
            html_parts.append(self._process_element_html(element))

        html_parts.append('</body>')
        html_parts.append('</html>')

        return '\n'.join(html_parts)

    def to_markdown(self) -> str:
        """
        Convert document to Markdown format.

        Returns:
            Markdown string with preserved formatting
        """
        markdown_parts = []

        for element in self.content:
            markdown_parts.append(self._process_element_markdown(element))

        return '\n'.join(markdown_parts)

    def to_text(self) -> str:
        """
        Convert document to plain text.

        Returns:
            Plain text string
        """
        text_parts = []

        for element in self.content:
            text_parts.append(self._process_element_text(element))

        return '\n'.join(text_parts)

    def _process_element_html(self, element: Dict[str, Any]) -> str:
        """Process a single element and convert to HTML."""
        if 'paragraph' in element:
            return self._process_paragraph_html(element['paragraph'])
        elif 'table' in element:
            return self._process_table_html(element['table'])
        return ''

    def _process_paragraph_html(self, paragraph: Dict[str, Any]) -> str:
        """Process a paragraph element to HTML."""
        elements = paragraph.get('elements', [])
        if not elements:
            return '<p></p>'

        # Check for heading style
        style = paragraph.get('paragraphStyle', {})
        named_style = style.get('namedStyleType', 'NORMAL_TEXT')

        # Determine tag based on style
        tag = 'p'
        if named_style == 'HEADING_1':
            tag = 'h1'
        elif named_style == 'HEADING_2':
            tag = 'h2'
        elif named_style == 'HEADING_3':
            tag = 'h3'
        elif named_style == 'HEADING_4':
            tag = 'h4'
        elif named_style == 'HEADING_5':
            tag = 'h5'
        elif named_style == 'HEADING_6':
            tag = 'h6'

        # Get bullet/numbering info
        bullet = paragraph.get('bullet')

        content_parts = []
        for elem in elements:
            if 'textRun' in elem:
                text_run = elem['textRun']
                content = text_run.get('content', '')
                style = text_run.get('textStyle', {})

                # Apply text formatting
                formatted_text = self._apply_text_style_html(content, style)
                content_parts.append(formatted_text)

        full_content = ''.join(content_parts).rstrip('\n')

        if bullet:
            # Return as list item
            return f'<li>{full_content}</li>'
        else:
            return f'<{tag}>{full_content}</{tag}>'

    def _apply_text_style_html(self, text: str, style: Dict[str, Any]) -> str:
        """Apply text styling (bold, italic, etc.) in HTML."""
        if not text or text == '\n':
            return text

        result = text

        # Apply bold
        if style.get('bold'):
            result = f'<strong>{result}</strong>'

        # Apply italic
        if style.get('italic'):
            result = f'<em>{result}</em>'

        # Apply underline
        if style.get('underline'):
            result = f'<u>{result}</u>'

        # Apply strikethrough
        if style.get('strikethrough'):
            result = f'<del>{result}</del>'

        # Apply link
        if 'link' in style:
            url = style['link'].get('url', '')
            result = f'<a href="{url}">{result}</a>'

        return result

    def _process_table_html(self, table: Dict[str, Any]) -> str:
        """Process a table element to HTML."""
        html = ['<table border="1" cellpadding="5" cellspacing="0">']

        for row in table.get('tableRows', []):
            html.append('<tr>')
            for cell in row.get('tableCells', []):
                cell_content = []
                for element in cell.get('content', []):
                    if 'paragraph' in element:
                        cell_content.append(self._process_paragraph_html(element['paragraph']))
                html.append(f'<td>{"".join(cell_content)}</td>')
            html.append('</tr>')

        html.append('</table>')
        return '\n'.join(html)

    def _process_element_markdown(self, element: Dict[str, Any]) -> str:
        """Process a single element and convert to Markdown."""
        if 'paragraph' in element:
            return self._process_paragraph_markdown(element['paragraph'])
        return ''

    def _process_paragraph_markdown(self, paragraph: Dict[str, Any]) -> str:
        """Process a paragraph element to Markdown."""
        elements = paragraph.get('elements', [])
        if not elements:
            return ''

        # Check for heading style
        style = paragraph.get('paragraphStyle', {})
        named_style = style.get('namedStyleType', 'NORMAL_TEXT')

        # Determine prefix based on style
        prefix = ''
        if named_style == 'HEADING_1':
            prefix = '# '
        elif named_style == 'HEADING_2':
            prefix = '## '
        elif named_style == 'HEADING_3':
            prefix = '### '
        elif named_style == 'HEADING_4':
            prefix = '#### '
        elif named_style == 'HEADING_5':
            prefix = '##### '
        elif named_style == 'HEADING_6':
            prefix = '###### '

        # Get bullet/numbering info
        bullet = paragraph.get('bullet')

        content_parts = []
        for elem in elements:
            if 'textRun' in elem:
                text_run = elem['textRun']
                content = text_run.get('content', '')
                style = text_run.get('textStyle', {})

                # Apply text formatting
                formatted_text = self._apply_text_style_markdown(content, style)
                content_parts.append(formatted_text)

        full_content = ''.join(content_parts).rstrip('\n')

        if bullet:
            # Return as list item
            return f'* {full_content}'
        else:
            return f'{prefix}{full_content}'

    def _apply_text_style_markdown(self, text: str, style: Dict[str, Any]) -> str:
        """Apply text styling (bold, italic, etc.) in Markdown."""
        if not text or text == '\n':
            return text

        result = text

        # Apply bold and italic together
        if style.get('bold') and style.get('italic'):
            result = f'***{result}***'
        elif style.get('bold'):
            result = f'**{result}**'
        elif style.get('italic'):
            result = f'*{result}*'

        # Apply strikethrough
        if style.get('strikethrough'):
            result = f'~~{result}~~'

        # Apply link
        if 'link' in style:
            url = style['link'].get('url', '')
            result = f'[{result}]({url})'

        return result

    def _process_element_text(self, element: Dict[str, Any]) -> str:
        """Process a single element and convert to plain text."""
        if 'paragraph' in element:
            return self._process_paragraph_text(element['paragraph'])
        return ''

    def _process_paragraph_text(self, paragraph: Dict[str, Any]) -> str:
        """Process a paragraph element to plain text."""
        elements = paragraph.get('elements', [])
        if not elements:
            return ''

        content_parts = []
        for elem in elements:
            if 'textRun' in elem:
                text_run = elem['textRun']
                content = text_run.get('content', '')
                content_parts.append(content)

        return ''.join(content_parts).rstrip('\n')
