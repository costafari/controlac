package sv.com.genius.controlac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import sv.com.genius.controlac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clientes.class);
        Clientes clientes1 = new Clientes();
        clientes1.setId(1L);
        Clientes clientes2 = new Clientes();
        clientes2.setId(clientes1.getId());
        assertThat(clientes1).isEqualTo(clientes2);
        clientes2.setId(2L);
        assertThat(clientes1).isNotEqualTo(clientes2);
        clientes1.setId(null);
        assertThat(clientes1).isNotEqualTo(clientes2);
    }
}
