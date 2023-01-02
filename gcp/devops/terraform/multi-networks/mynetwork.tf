resource "google_compute_network" "mynetwork" {
  name = "mynetwork"
  # RESOURCE properties go here
  auto_create_subnetworks = "true"
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

resource "google_compute_firewall" "mynetwork-allow-custom" {
  name = "mynetwork-allow-custom"
  allow {
    protocol = "all"
  }
  direction     = "INGRESS"
  network = google_compute_network.mynetwork.id
  priority      = 65534
  source_ranges = ["10.128.0.0/9"]
}
