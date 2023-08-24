package sv.com.genius.controlac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sv.com.genius.controlac.web.rest.TestUtil;

class ProductoAuditTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoAudit.class);
        ProductoAudit productoAudit1 = new ProductoAudit();
        productoAudit1.setId(1L);
        ProductoAudit productoAudit2 = new ProductoAudit();
        productoAudit2.setId(productoAudit1.getId());
        assertThat(productoAudit1).isEqualTo(productoAudit2);
        productoAudit2.setId(2L);
        assertThat(productoAudit1).isNotEqualTo(productoAudit2);
        productoAudit1.setId(null);
        assertThat(productoAudit1).isNotEqualTo(productoAudit2);
    }
}
