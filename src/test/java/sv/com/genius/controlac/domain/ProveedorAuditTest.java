package sv.com.genius.controlac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sv.com.genius.controlac.web.rest.TestUtil;

class ProveedorAuditTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProveedorAudit.class);
        ProveedorAudit proveedorAudit1 = new ProveedorAudit();
        proveedorAudit1.setId(1L);
        ProveedorAudit proveedorAudit2 = new ProveedorAudit();
        proveedorAudit2.setId(proveedorAudit1.getId());
        assertThat(proveedorAudit1).isEqualTo(proveedorAudit2);
        proveedorAudit2.setId(2L);
        assertThat(proveedorAudit1).isNotEqualTo(proveedorAudit2);
        proveedorAudit1.setId(null);
        assertThat(proveedorAudit1).isNotEqualTo(proveedorAudit2);
    }
}
