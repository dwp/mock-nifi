curl --cert certificate.pem:changeit --key key.pem -kw RESPONSE: %{http_code}\n -X POST -H Content-type: text/plain -H filename: abc.txt -H collection: poo --data-binary hello world https://localhost:8443/collections
curl --cert certificate.pem:changeit --key key.pem -kw RESPONSE: %{http_code}\n -X POST -H Content-type: text/plain -H filename: abc.txt -H collection: poo --data-binary hello world https://localhost:8443/collections
RESPONSE: 000
/data/output/poo/abc.txt
RESPONSE: 200
