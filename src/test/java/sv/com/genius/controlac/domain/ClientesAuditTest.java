package sv.com.genius.controlac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sv.com.genius.controlac.web.rest.TestUtil;

class ClientesAuditTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientesAudit.class);
        ClientesAudit clientesAudit1 = new ClientesAudit();
        clientesAudit1.setId(1L);
        ClientesAudit clientesAudit2 = new ClientesAudit();
        clientesAudit2.setId(clientesAudit1.getId());
        assertThat(clientesAudit1).isEqualTo(clientesAudit2);
        clientesAudit2.setId(2L);
        assertThat(clientesAudit1).isNotEqualTo(clientesAudit2);
        clientesAudit1.setId(null);
        assertThat(clientesAudit1).isNotEqualTo(clientesAudit2);
    }
}
