FROM nginx:1.17.6-alpine 

COPY ./nginx.conf /etc/nginx/nginx.conf

# RUN apk add --no-cache certbot certbot-nginx

# CMD sh -c 'certbot --nginx --domains poupa.online,api.poupa.online --email leandrosantino2013@gmail.com --agree-tos --redirect --reinstall --non-interactive; \
# nginx -s reload; \
# while true; do \
# echo "Atualizando certificados"; \
# certbot renew --non-interactive; \
# nginx -s reload; \
# sleep 604810 & SLEEP_PID=$!; \
# wait "$SLEEP_PID"; \
# done'