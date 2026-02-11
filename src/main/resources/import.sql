-- categories
INSERT INTO categories (name, slug, description, sort_order, is_visible) VALUES ('Coffee', 'coffee', 'Coffee products', 10, TRUE);
INSERT INTO categories (name, slug, description, sort_order, is_visible) VALUES ('Tea', 'tea', 'Tea products', 20, TRUE);

-- products
INSERT INTO products (name, price, description, stock_qty, image_url, category_id, created_at, updated_at) VALUES ('Coffee Beans 500g', 1200.00, 'Arabica beans', 30, 'https://picsum.photos/seed/coffee/600/600', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, description, stock_qty, image_url, category_id, created_at, updated_at) VALUES ('Tea Leaves 200g', 800.00, 'Darjeeling', 50, 'https://picsum.photos/seed/tea/600/600', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO products (name, price, description, stock_qty, image_url, category_id, created_at, updated_at) VALUES ('Chocolate Bar', 250.00, 'Dark 70%', 200, 'https://picsum.photos/seed/choco/600/600', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 代表的に使われる "password" のbcryptハッシュ（コスト10）
-- 例：$2a$10$7EqJtq98hPqEX7fNZaFWoO.J8vtQ6Uiq5FvlaZ6Y.j3B3VY6Yf/DO
-- どちらのユーザーも同じハッシュを使っています（学習用）
INSERT INTO users (email, password_hash, display_name, role, enabled, created_at, updated_at)
VALUES ('admin@example.com','$2a$10$N7s8J2HQxWR5t.6cw8hreuEaXhn.puodBPzE0sLQKL55yuGui3fs6','Administrator', 'ADMIN', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (email, password_hash, display_name, role, enabled, created_at, updated_at)
VALUES ('user@example.com','$2a$10$N7s8J2HQxWR5t.6cw8hreuEaXhn.puodBPzE0sLQKL55yuGui3fs6','Sample User', 'USER', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);