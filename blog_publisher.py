#!/usr/bin/env python3
"""
Google Docs Blog Publisher
Publishes blog posts from Google Docs while preserving formatting.
"""

import os
import sys
import argparse
import configparser
from pathlib import Path

from google_docs_client import GoogleDocsClient
from document_formatter import DocumentFormatter


def load_config(config_file='config.ini'):
    """Load configuration from file."""
    if not os.path.exists(config_file):
        print(f"Configuration file not found: {config_file}")
        print("Please create config.ini based on config.example.ini")
        sys.exit(1)

    config = configparser.ConfigParser()
    config.read(config_file)
    return config


def main():
    """Main function to run the blog publisher."""
    parser = argparse.ArgumentParser(
        description='Publish blog posts from Google Docs with preserved formatting'
    )
    parser.add_argument(
        '--document-id',
        help='Google Docs document ID (overrides config file)'
    )
    parser.add_argument(
        '--format',
        choices=['html', 'markdown', 'text'],
        help='Output format (overrides config file)'
    )
    parser.add_argument(
        '--output',
        help='Output file path (overrides config file)'
    )
    parser.add_argument(
        '--config',
        default='config.ini',
        help='Configuration file path (default: config.ini)'
    )

    args = parser.parse_args()

    # Load configuration
    config = load_config(args.config)

    # Get document ID
    document_id = args.document_id or config.get('google', 'document_id')
    if not document_id or document_id == 'YOUR_DOCUMENT_ID_HERE':
        print("Error: Please provide a Google Docs document ID")
        print("Either use --document-id or set it in config.ini")
        sys.exit(1)

    # Get output format
    output_format = args.format or config.get('output', 'format', fallback='html')

    # Get output file
    output_file = args.output or config.get('output', 'output_file', fallback=f'output/blog_post.{output_format}')

    print(f"Fetching document: {document_id}")

    # Initialize Google Docs client
    client = GoogleDocsClient()

    try:
        # Authenticate and get document
        client.authenticate()
        document = client.get_document(document_id)

        print(f"Document title: {document.get('title', 'Untitled')}")
        print(f"Converting to {output_format.upper()} format...")

        # Format document
        formatter = DocumentFormatter(document)

        if output_format == 'html':
            content = formatter.to_html()
        elif output_format == 'markdown':
            content = formatter.to_markdown()
        else:
            content = formatter.to_text()

        # Create output directory if needed
        output_path = Path(output_file)
        output_path.parent.mkdir(parents=True, exist_ok=True)

        # Write to file
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write(content)

        print(f"✓ Blog post saved to: {output_file}")
        print(f"✓ Successfully published!")

    except FileNotFoundError as e:
        print(f"Error: {e}")
        sys.exit(1)
    except Exception as e:
        print(f"An error occurred: {e}")
        sys.exit(1)


if __name__ == '__main__':
    main()
