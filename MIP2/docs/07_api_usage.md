# API Usage

## API Endpoint
https://api.thecatapi.com/v1/images/search

## Request
GET /v1/images/search?limit=10

## Example Response
[
  {
    "id": "abc123",
    "url": "https://cdn2.thecatapi.com/images/abc123.jpg",
    "width": 1200,
    "height": 800
  }
]

## Notes
- Returns an array of image objects
- No authentication required for basic usage (optional API key)