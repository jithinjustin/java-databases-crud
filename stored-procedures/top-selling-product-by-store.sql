DELIMITER $$
CREATE PROCEDURE GetTopSellingProductByStore(IN target_month INT, IN target_year INT)
BEGIN
SELECT
    p.name AS product_name,
    od.store_id,
    SUM(oi.quantity) AS total_quantity_sold,
    SUM(oi.price * oi.quantity) AS total_sales
FROM
    product p
        JOIN
    order_item oi ON p.id = oi.product_id
        JOIN
    order_details od ON oi.order_id = od.id
WHERE
    MONTH(od.date) = target_month  -- Use the provided month
  AND YEAR(od.date) = target_year  -- Use the provided year
GROUP BY
    od.store_id, p.name  -- Group by store and product name
HAVING
    SUM(oi.quantity) = (
    SELECT
    MAX(total_quantity)
    FROM (
    SELECT
    SUM(oi2.quantity) AS total_quantity
    FROM
    order_item oi2
    JOIN
    order_details od2 ON oi2.order_id = od2.id
    JOIN
    product p2 ON oi2.product_id = p2.id
    WHERE
    MONTH(od2.date) = target_month  -- Same month
   AND YEAR(od2.date) = target_year  -- Same year
   AND od2.store_id = od.store_id  -- Same store
    GROUP BY
    p2.name  -- Group by product name
    ) AS Subquery
    )
ORDER BY
    od.store_id;
END$$
DELIMITER ;