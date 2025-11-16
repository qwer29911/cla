#!/bin/bash
# Setup script for Google Docs Blog Publisher

echo "=== Google Docs Blog Publisher - Setup ==="
echo ""

# Check if Python is installed
if ! command -v python3 &> /dev/null; then
    echo "Error: Python 3 is not installed"
    echo "Please install Python 3.7 or newer"
    exit 1
fi

echo "✓ Python 3 found: $(python3 --version)"
echo ""

# Create virtual environment
echo "Creating virtual environment..."
python3 -m venv venv

# Activate virtual environment
echo "Activating virtual environment..."
source venv/bin/activate

# Install requirements
echo "Installing dependencies..."
pip install -r requirements.txt

echo ""
echo "=== Setup Complete! ==="
echo ""
echo "Next steps:"
echo "1. Create credentials.json from Google Cloud Console"
echo "2. Copy config.example.ini to config.ini and configure it"
echo "3. Run: source venv/bin/activate"
echo "4. Run: python blog_publisher.py"
echo ""
