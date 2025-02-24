using System.Collections.Generic;
using UnityEngine;

public class BVHNode {
    // Bounding volume que engloba os objetos
    public Bounds bounds;

    // Lista de nós filhos (null se for folha)
    public List<BVHNode> children;

    // Lista de colliders (usada apenas em nós folha)
    public List<Collider> colliders;

    // Construtor
    public BVHNode(List<Collider> colliders) {
        this.colliders = colliders;
        bounds = new Bounds();

        // Calcula o bounding volume que engloba todos os colliders
        foreach (var collider in colliders) {
            bounds.Encapsulate(collider.bounds);
        }
    }

    // Verifica se um raio intersecta o bounding volume
    public bool Intersects(Ray ray) {
        return bounds.IntersectRay(ray);
    }

    // Método para desenhar o bounding volume (usando Gizmos)
    public void DrawGizmos() {
        Gizmos.color = Color.green;
        Gizmos.DrawWireCube(bounds.center, bounds.size);

        // Desenha os bounding volumes dos filhos (se houver)
        if (children != null) {
            foreach (var child in children) {
                child.DrawGizmos();
            }
        }
    }
}