curl --cert certificate.pem:changeit --key key.pem -kw RESPONSE: %{http_code}\n -X POST -H Content-type: text/plain -H Filename: abc.txt -H Collection: poo --data-binary hello world https://localhost:8443/collection
curl --cert certificate.pem:changeit --key key.pem -kw RESPONSE: %{http_code}\n -X POST -H Content-type: text/plain -H Filename: abc.txt -H Collection: poo --data-binary hello world https://localhost:8443/collection
RESPONSE: 000
/data/output/poo/abc.txt
RESPONSE: 200
