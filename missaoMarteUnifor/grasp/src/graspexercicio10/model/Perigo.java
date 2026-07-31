package graspexercicio10.model;

/**
 * GRASP Polymorphism: interface unificada para todos os obstáculos da missão.
 *
 * Antes da migração, Main.desenharMapa() usava instanceof para identificar o símbolo
 * de cada tipo de perigo. Com esta interface, cada classe declara seu próprio símbolo,
 * eliminando condicionais de tipo no código cliente.
 */
public interface Perigo {
    int getX();
    int getY();
    boolean colideCom(Nave nave);
    char getSimbolo();
}
