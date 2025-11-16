"""
Google Docs API Client
Handles authentication and document retrieval from Google Docs.
"""

import os.path
from google.auth.transport.requests import Request
from google.oauth2.credentials import Credentials
from google_auth_oauthlib.flow import InstalledAppFlow
from googleapiclient.discovery import build
from googleapiclient.errors import HttpError

# If modifying these scopes, delete the file token.json.
SCOPES = ['https://www.googleapis.com/auth/documents.readonly']


class GoogleDocsClient:
    """Client for interacting with Google Docs API."""

    def __init__(self, credentials_file='credentials.json', token_file='token.json'):
        """
        Initialize the Google Docs client.

        Args:
            credentials_file: Path to credentials.json from Google Cloud Console
            token_file: Path to store the user's access token
        """
        self.credentials_file = credentials_file
        self.token_file = token_file
        self.creds = None
        self.service = None

    def authenticate(self):
        """Authenticate with Google Docs API."""
        # The file token.json stores the user's access and refresh tokens
        if os.path.exists(self.token_file):
            self.creds = Credentials.from_authorized_user_file(self.token_file, SCOPES)

        # If there are no (valid) credentials available, let the user log in
        if not self.creds or not self.creds.valid:
            if self.creds and self.creds.expired and self.creds.refresh_token:
                self.creds.refresh(Request())
            else:
                if not os.path.exists(self.credentials_file):
                    raise FileNotFoundError(
                        f"Credentials file not found: {self.credentials_file}\n"
                        "Please download credentials.json from Google Cloud Console:\n"
                        "https://console.cloud.google.com/apis/credentials"
                    )
                flow = InstalledAppFlow.from_client_secrets_file(
                    self.credentials_file, SCOPES)
                self.creds = flow.run_local_server(port=0)

            # Save the credentials for the next run
            with open(self.token_file, 'w') as token:
                token.write(self.creds.to_json())

        self.service = build('docs', 'v1', credentials=self.creds)

    def get_document(self, document_id):
        """
        Retrieve a Google Docs document.

        Args:
            document_id: The ID of the Google Docs document

        Returns:
            Document content as a dictionary
        """
        if not self.service:
            self.authenticate()

        try:
            document = self.service.documents().get(documentId=document_id).execute()
            return document
        except HttpError as err:
            print(f'An error occurred: {err}')
            raise
