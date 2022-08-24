
DELETE FROM restaurante_forma_pagamento;
ALTER TABLE restaurante_forma_pagamento ADD PRIMARY KEY(restaurante_id, forma_pagamento_id);