using System.Collections.Generic;
using UnityEngine;

public class BVH {
    // Nó raiz da hierarquia
    public BVHNode root;

    // Construtor
    public BVH(List<Collider> colliders) {
        // Cria a hierarquia de bounding volumes
        root = BuildHierarchy(colliders);
    }

    // Método para construir a hierarquia
    private BVHNode BuildHierarchy(List<Collider> colliders) {
        if (colliders.Count == 0) return null;

        // Cria um nó folha se houver poucos colliders
        if (colliders.Count <= 2) {
            return new BVHNode(colliders);
        }

        // Divide os colliders em dois grupos
        List<Collider> leftColliders = new List<Collider>();
        List<Collider> rightColliders = new List<Collider>();

        // Divide os colliders ao longo do eixo X (pode ser Y ou Z)
        colliders.Sort((a, b) => a.bounds.center.x.CompareTo(b.bounds.center.x));
        int mid = colliders.Count / 2;
        leftColliders = colliders.GetRange(0, mid);
        rightColliders = colliders.GetRange(mid, colliders.Count - mid);

        // Cria nós filhos recursivamente
        BVHNode node = new BVHNode(colliders);
        node.children = new List<BVHNode> {
            BuildHierarchy(leftColliders),
            BuildHierarchy(rightColliders)
        };

        return node;
    }

    // Método para verificar colisões com um raio
    public bool Raycast(Ray ray, out RaycastHit hit) {
        hit = new RaycastHit();
        return RaycastRecursive(root, ray, out hit);
    }

    // Método recursivo para verificar colisões
    private bool RaycastRecursive(BVHNode node, Ray ray, out RaycastHit hit) {
        if (node == null) {
            hit = new RaycastHit();
            return false;
        }

        // Verifica se o raio intersecta o bounding volume do nó
        if (node.Intersects(ray)) {
            // Se for um nó folha, verifica colisões com os colliders
            if (node.children == null) {
                foreach (var collider in node.colliders) {
                    if (collider.Raycast(ray, out hit, Mathf.Infinity)) {
                        return true;
                    }
                }
            } else {
                // Se não for folha, verifica os filhos recursivamente
                foreach (var child in node.children) {
                    if (RaycastRecursive(child, ray, out hit)) {
                        return true;
                    }
                }
            }
        }

        hit = new RaycastHit();
        return false;
    }
}