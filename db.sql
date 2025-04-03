-- Limpe as tabelas (opcional, se já existirem dados)
DELETE FROM stuff_category;
DELETE FROM stuff;
DELETE FROM category;
ALTER TABLE stuff ALTER COLUMN id RESTART WITH 1;
ALTER TABLE category ALTER COLUMN id RESTART WITH 1;

-- Insere categorias
INSERT INTO category (name, description, status) VALUES
('Categoria A', 'Coisas da categoria A', 'ON'),
('Categoria B', 'Coisas da categoria B', 'ON'),
('Categoria C', 'Coisas da categoria C', 'ON'),
('Categoria D', 'Coisas da categoria D', 'ON'),
('Categoria E', 'Coisas da categoria E', 'ON'),
('Categoria F', 'Coisas da categoria F', 'ON'),
('Categoria G', 'Coisas da categoria G', 'ON'),
('Categoria H', 'Coisas da categoria H', 'ON');

-- Insere itens (stuff)
INSERT INTO stuff (date, name, description, location, photo, status) VALUES
(CURRENT_TIMESTAMP() - 190, 'Coisa A', 'Descrição da coisa A', 'Local da coisa A', 'https://picsum.photos/196', 'ON'),
(CURRENT_TIMESTAMP() - 180, 'Coisa B', 'Descrição da coisa B', 'Local da coisa B', 'https://picsum.photos/197', 'ON'),
(CURRENT_TIMESTAMP() - 170, 'Coisa C', 'Descrição da coisa C', 'Local da coisa C', 'https://picsum.photos/198', 'ON'),
(CURRENT_TIMESTAMP() - 160, 'Coisa D', 'Descrição da coisa D', 'Local da coisa D', 'https://picsum.photos/199', 'ON'),
(CURRENT_TIMESTAMP() - 150, 'Coisa E', 'Descrição da coisa E', 'Local da coisa E', 'https://picsum.photos/200', 'ON'),
(CURRENT_TIMESTAMP() - 140, 'Coisa F', 'Descrição da coisa F', 'Local da coisa F', 'https://picsum.photos/201', 'ON'),
(CURRENT_TIMESTAMP() - 130, 'Coisa G', 'Descrição da coisa G', 'Local da coisa G', 'https://picsum.photos/202', 'ON'),
(CURRENT_TIMESTAMP() - 120, 'Coisa H', 'Descrição da coisa H', 'Local da coisa H', 'https://picsum.photos/203', 'ON'),
(CURRENT_TIMESTAMP() - 110, 'Coisa I', 'Descrição da coisa I', 'Local da coisa I', 'https://picsum.photos/204', 'ON'),
(CURRENT_TIMESTAMP() - 100, 'Coisa J', 'Descrição da coisa J', 'Local da coisa J', 'https://picsum.photos/205', 'ON');

-- Associações entre Stuff e Categories
INSERT INTO stuff_category (stuff_id, category_id) VALUES
(1, 1), (1, 3), (1, 5), -- Coisa A → Categorias A, C, E
(2, 2), (2, 4),        -- Coisa B → Categorias B, D
(3, 1), (3, 6),        -- Coisa C → Categorias A, F
(4, 3), (4, 7),        -- Coisa D → Categorias C, G
(5, 5), (5, 8),        -- Coisa E → Categorias E, H
(6, 2), (6, 6), (6, 8),-- Coisa F → Categorias B, F, H
(7, 4), (7, 7),        -- Coisa G → Categorias D, G
(8, 1), (8, 5), (8, 7),-- Coisa H → Categorias A, E, G
(9, 3), (9, 6),        -- Coisa I → Categorias C, F
(10, 2), (10, 4), (10, 8); -- Coisa J → Categorias B, D, H