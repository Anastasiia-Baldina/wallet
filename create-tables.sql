create table if not exists bank_account (
    id      bigint  not null,
    name    varchar not null,
    balance bigint  not null
);

create table if not exists category (
    id      bigint  not null,
    name    varchar not null,
    type    varchar not null
);

create table if not exists operation (
    id                  bigint      not null,
    bank_account_id     bigint      not null,
    category_id         bigint      not null,
    date                date        not null,
    amount              bigint      not null,
    description         varchar     null,
    type                varchar     not null
);