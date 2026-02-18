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

SELECT id_ingredient,unit,
       COALESCE(SUM(
                        CASE
                            WHEN type = 'IN'  THEN quantity
                            WHEN type = 'OUT' THEN -quantity
                            END
                ), 0) AS actual_quantity

FROM StockMovement
WHERE   id_ingredient = ? and  creation_datetime <=?
GROUP BY id_ingredient,unit;
-- Énoncé : Calculer le total cumulé des factures PAID, CONFIRMED et DRAFT dans une
-- seule ligne.
-- Signature méthode en Java : InvoiceStatusTotals computeStatusTotals();
-- Résultat attendu lors de l’affichage dans le Main :
-- total_paid = 700.00
-- total_confirmed = 250.00
-- total_draft = 150.00

select SUM(il.quantity * il.unit_price) AS total_paid from invoice i join invoice_line il on i.id = il.invoice_id where i.status = 'PAID';

select SUM(il.quantity * il.unit_price) AS total_confirmed from invoice i join invoice_line il on i.id = il.invoice_id where i.status = 'CONFIRMED';

select SUM(il.quantity * il.unit_price) AS total_draft from invoice i join invoice_line il on i.id = il.invoice_id where i.status = 'DRAFT';

select sum(
       case
          ( when status = 'PAID' then sum(il.quantity * il.unit_price) ) as total_paid
      ( when status = 'CONFIRMED' then sum(il.quantity * il.unit_price) ) as total_confirmed
      ( when status = 'DRAFT' then sum(il.quantity * il.unit_price) ) as total_draft
       )
from invoice i join invoice_line il on i.id = il.invoice_id;

select total_paid from invoice where ( (select sum( when status = 'PAID' then sum(il.quantity * il.unit_price)) from when status = 'PAID' then sum(il.quantity * il.unit_price)) from invoice i join invoice_line il on i.id = il.invoice_id);

SELECT * FROM Employees
WHERE Salary = (SELECT MAX(Salary) FROM Employees);


CASE when status = 'CONFIRMED' then +quantity as confirmed
case when status = 'DRAFT' then +quantity as draft

SELECT
    CASE
        WHEN  status = 'PAID' then sum(il.quantity * il.unit_price)
        when status = 'CONFIRMED' then sum(il.quantity * il.unit_price)
        when status = 'DRAFT' then sum(il.quantity * il.unit_price)
        END

from invoice i join invoice_line il on i.id = il.invoice_id group by i.status;

SELECT
    SUM(CASE
            WHEN i.status = 'PAID'
                THEN il.quantity * il.unit_price
            ELSE 0
        END) AS total_paid,

    SUM(CASE
            WHEN i.status = 'CONFIRMED'
                THEN il.quantity * il.unit_price
            ELSE 0
        END) AS total_confirmed,

    SUM(CASE
            WHEN i.status = 'DRAFT'
                THEN il.quantity * il.unit_price
            ELSE 0
        END) AS total_draft

FROM invoice i
         JOIN invoice_line il ON i.id = il.invoice_id;


select sum(   (CASE
                       WHEN i.status = 'PAID'
                           THEN il.quantity * il.unit_price
                       ELSE 0
    END ) +  (CASE
                                   WHEN i.status = 'CONFIRMED'
                                       THEN (il.quantity * il.unit_price) / 2
                                   ELSE 0
    END) ) as computeWeight

FROM invoice i
         JOIN invoice_line il ON i.id = il.invoice_id;

select SUM(il.quantity * il.unit_price) as HT ,
       SUM((il.quantity * il.unit_price)*(0.2)) as TVA ,
       SUM( (il.quantity * il.unit_price)+ ((il.quantity * il.unit_price)*(0.2))) AS TTC
FROM invoice i JOIN invoice_line il ON i.id = il.invoice_id group by invoice_id;

SELECT
    SUM(line_total) AS ht,
    SUM(line_total * 0.2) AS tva,
    SUM(line_total * 1.2) AS ttc
FROM (
         SELECT
             i.id AS invoice_id,
             (il.quantity * il.unit_price) AS line_total
         FROM invoice i
                  JOIN invoice_line il ON i.id = il.invoice_id
     ) sub
GROUP BY invoice_id;
