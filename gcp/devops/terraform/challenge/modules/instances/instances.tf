resource "google_compute_instance" "vm_instance" {
  name         = "${var.instance_name}"
  zone         = "${var.instance_zone}"
  machine_type = "e2-standard-2"
  allow_stopping_for_update = true

  boot_disk {
    initialize_params {
      image = "projects/debian-cloud/global/images/debian-11-bullseye-v20231115"
      }
  }

  metadata_startup_script = "sudo yum install wget -y ; wget https://github.com/kylinsoong/ttcp/releases/download/1.13-3/ttcp-1.13-3.x86_64.rpm"

  network_interface {
    subnetwork = "${var.instance_subnetwork}"
    access_config {
      # Allocate a one-to-one NAT IP to the instance
    }
  }
}