variable "region" {
  description = "The name of the region."
  type        = string
  default     = "us-east1"
}

variable "zone" {
  description = "The name of the zone."
  type        = string
  default     = "us-east1-b"
}


variable "project_id" {
  description = "The ID of the project to create the bucket in."
  type        = string
  default     = "qwiklabs-gcp-02-dfe0bf65a90a"
}
