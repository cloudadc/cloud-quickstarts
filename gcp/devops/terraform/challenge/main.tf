terraform {
  backend "gcs" {
    bucket  = "tf-bucket-486529"
    prefix  = "terraform/state"
  }
}

provider "google" {

  #
  region  = var.region
  zone    = var.zone
}

resource "google_compute_network" "tf-vpc-242976" {
  name                    = "tf-vpc-242976"
  auto_create_subnetworks = false
  mtu                     = 1460
}

resource "google_compute_subnetwork" "subnet-01" {
  name          = "subnet-01"
  ip_cidr_range = "10.10.10.0/24"
  region        = "us-east1"
  network       = google_compute_network.tf-vpc-242976.id
}

resource "google_compute_subnetwork" "subnet-02" {
  name          = "subnet-02"
  ip_cidr_range = "10.10.20.0/24"
  region        = "us-east1"
  network       = google_compute_network.tf-vpc-242976.id
}

resource "google_compute_firewall" "tf-vpc-242976" {
  name    = "tf-firewall"
  network = google_compute_network.tf-vpc-242976.name

  direction     = "INGRESS"
  allow {
    protocol = "tcp"
    ports    = ["80"]
  }
  source_ranges = ["0.0.0.0/0"]
}


module "tf-instance-1" {
  source           = "./modules/instances"
  instance_name    = "tf-instance-1"
  instance_zone    = var.zone
  instance_subnetwork = google_compute_subnetwork.subnet-01.id
} 

module "tf-instance-2" {
  source           = "./modules/instances"
  instance_name    = "tf-instance-2"
  instance_zone    = var.zone
  instance_subnetwork = google_compute_subnetwork.subnet-02.id
}

module "tf-bucket-486529" {
    source           = "./modules/storage"
}

