
DELETE FROM usuario_grupo;
ALTER TABLE usuario_grupo ADD PRIMARY KEY(usuario_id, grupo_id);