import os
import functions_framework
import firebase_admin
from firebase_admin import credentials, db
from po10_api import Client

# Initialize Firebase app once
if not firebase_admin._apps:
    cred = credentials.ApplicationDefault()
    firebase_admin.initialize_app(cred, {
        'databaseURL': os.environ.get('FIREBASE_DATABASE_URL')
    })

@functions_framework.http
def sync_rankings(request):
    """Scheduled function triggered by Cloud Scheduler."""
    athlete_id = os.environ.get('PO10_ATHLETE_ID')
    if not athlete_id:
        return ('PO10_ATHLETE_ID not set', 500)

    client = Client()
    profile = client.get_athlete_profile(athlete_id)
    data = {
        'rankingTable': profile.get('rankingTable', []),
        'pbTable': profile.get('pbTable', [])
    }
    db.reference(f'/rankings/{athlete_id}').set(data)
    return ('ok', 200)

