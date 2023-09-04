FROM python:3.9

WORKDIR /app

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY external_dns.py .

CMD ["python", "external_dns.py"]

