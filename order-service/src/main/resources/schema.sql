



create table  if not exists orders
(
    id              BIGSERIAL primary key,
    user_id         bigint         not null,
    order_date      timestamp      not null,
    status          integer        not null,
    total_price     decimal(10, 2) not null,
    billing_address varchar(400),
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateTime      TIMESTAMP
);
create table if not exists order_items
(
    id         BIGSERIAL primary key,
    order_id   bigint         not null,
    product_id varchar(50)         not null,
    product_name varchar(200) not null,
    quantity   integer        not null,
    price decimal(10, 2) not null,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP,
    discount integer default 0,
    foreign key (order_id) references orders (id) on delete cascade
);
create index idx_orders_user_id on orders (user_id);
create index idx_order_items_order_id on order_items (order_id);
create index idx_order_items_product_id on order_items (product_id);
create index idx_orders_status on orders (status);
create index idx_orders_create_time on orders (create_time);