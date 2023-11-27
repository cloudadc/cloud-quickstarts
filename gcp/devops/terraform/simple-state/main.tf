provider "google" {
  project     = "qwiklabs-gcp-01-940fba6e34ae"
  region      = "us-east4"
}

resource "google_storage_bucket" "test-bucket-for-state" {
  name        = "qwiklabs-gcp-01-940fba6e34ae"
  location    = "US"
  uniform_bucket_level_access = true
  force_destroy = true
}

terraform {
  backend "local" {
     path = "terraform/state/terraform.tfstate"
  }
  #backend "gcs" {
  #  bucket  = "qwiklabs-gcp-01-940fba6e34ae"
  #  prefix  = "terraform/state"
  #}
}
