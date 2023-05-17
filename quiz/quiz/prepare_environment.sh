echo "Creating App Engine app"
gcloud app create --region "us-central"

echo "Exporting GCLOUD_PROJECT"
export GCLOUD_PROJECT=$DEVSHELL_PROJECT_ID

echo "Installing dependencies"
mvn clean install

echo "Creating Datastore entities"
mvn exec:java@create-entities

echo "Project ID: $DEVSHELL_PROJECT_ID"
