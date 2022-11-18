
DELETE FROM grupo_permissao;
ALTER TABLE grupo_permissao ADD PRIMARY KEY(grupo_id, permissao_id);