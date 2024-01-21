package br.com.devsibre;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import br.com.devsibre.Domain.Entity.Patrimonio;


class PatrimonioModelTest {

    private Patrimonio patrimonio;

    @BeforeEach
    void setUp() {
        patrimonio = new Patrimonio(1L, "Descrição do patrimônio", 10, new BigDecimal("100.00"), "2023-07-07");
    }

    @Test
    void testGetId_p() {
        Assert.assertEquals(1L, patrimonio.getId_p().longValue());
    }

    @Test
    void testGetDescricao() {
        Assert.assertEquals("Descrição do patrimônio", patrimonio.getDescricao());
    }

    @Test
    void testGetQuantidade() {
        Assert.assertEquals(10, patrimonio.getQuantidade());
    }

    @Test
    void testGetPreco() {
        Assert.assertEquals(new BigDecimal("100.00"), patrimonio.getPreco());
    }

    @Test
    void testGetData() {
        Assert.assertEquals("2023-07-07", patrimonio.getData());
    }

    @Test
    void testEquals() {
        Patrimonio outroPatrimonio = new Patrimonio(1L, "Descrição do patrimônio", 10, new BigDecimal("100.00"), "2023-07-07");
        Assert.assertEquals(patrimonio, outroPatrimonio);
    }

    @Test
    void testNotEquals() {
        Patrimonio outroPatrimonio = new Patrimonio(2L, "Outra descrição", 5, new BigDecimal("50.00"), "2023-07-08");
        Assert.assertNotEquals(patrimonio, outroPatrimonio);
    }
}
