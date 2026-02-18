SELECT
    i.id,
    i.customer_name,
    SUM(il.quantity * il.unit_price) AS total_amount
FROM invoice i
         JOIN invoice_line il ON i.id = il.invoice_id
GROUP BY i.id, i.customer_name
ORDER BY i.id;
SELECT
    i.id,
    i.customer_name,
    i.status,
    SUM(il.quantity * il.unit_price) AS total_amount
FROM invoice i
         JOIN invoice_line il ON i.id = il.invoice_id
WHERE i.status IN ('CONFIRMED', 'PAID')
GROUP BY i.id, i.customer_name, i.status
ORDER BY i.id;