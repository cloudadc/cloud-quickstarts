resource "google_compute_network" "mynetwork" {
  name                    = "mynetwork"
  auto_create_subnetworks = false
  mtu                     = 1460
}

resource "google_compute_subnetwork" "subnet-1" {
  name          = "subnet-1"
  ip_cidr_range = "10.140.0.0/20"
  region        = "asia-east1"
  network       = google_compute_network.mynetwork.id
}

resource "google_compute_subnetwork" "subnet-2" {
  name          = "subnet-2"
  ip_cidr_range = "10.146.0.0/20"
  region        = "asia-northeast1"
  network       = google_compute_network.mynetwork.id
}

resource "google_compute_firewall" "mynetwork-allow-ssh" {
  name = "mynetwork-allow-ssh"
  allow {
    ports    = ["22"]
    protocol = "tcp"
  }
  direction     = "INGRESS"
  network       = google_compute_network.mynetwork.id
  priority      = 65534
  source_ranges = ["0.0.0.0/0"]
}

resource "google_compute_firewall" "mynetwork-allow-rdp" {
  name = "mynetwork-allow-rdp"
  allow {
    ports    = ["3389"]
    protocol = "tcp"
  }
  direction     = "INGRESS"
  network       = google_compute_network.mynetwork.id
  priority      = 65534
  source_ranges = ["0.0.0.0/0"]
}

resource "google_compute_firewall" "mynetwork-allow-icmp" {
  name = "mynetwork-allow-icmp"
  allow {
    protocol = "icmp"
  }
  direction     = "INGRESS"
  network       = google_compute_network.mynetwork.id
  priority      = 65534
  source_ranges = ["0.0.0.0/0"]
}

resource "google_compute_firewall" "mynetwork-allow-ttcp" {
  name = "mynetwork-allow-ttcp"
  allow {
    ports    = ["5001"]
    protocol = "tcp"
  }
  direction     = "INGRESS"
  network       = google_compute_network.mynetwork.id
  priority      = 65534
  source_ranges = ["10.140.0.0/20", "10.146.0.0/20"]
}

module "vm-1" {
  source           = "./instance"
  instance_name    = "vm-1"
  instance_zone    = "asia-east1-a"
  instance_subnetwork = google_compute_subnetwork.subnet-1.id
}

module "vm-2" {
  source           = "./instance"
  instance_name    = "vm-2"
  instance_zone    = "asia-east1-a"
  instance_subnetwork = google_compute_subnetwork.subnet-1.id
}

module "vm-3" {
  source           = "./instance"
  instance_name    = "vm-3"
  instance_zone    = "asia-east1-c"
  instance_subnetwork = google_compute_subnetwork.subnet-1.id
}

module "vm-4" {
  source           = "./instance"
  instance_name    = "vm-4"
  instance_zone    = "asia-northeast1-b"
  instance_subnetwork = google_compute_subnetwork.subnet-2.id
}
