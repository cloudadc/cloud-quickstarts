resource "google_storage_bucket" "tf-bucket-486529" {
  name        = "tf-bucket-486529"
  location    = "US"
  force_destroy = true
  #uniform_bucket_level_access = true
}