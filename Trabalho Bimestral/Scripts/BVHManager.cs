using System.Collections.Generic;
using UnityEngine;

public class BVHManager : MonoBehaviour {
    // Lista de inimigos na cena
    public List<Inimigo> inimigos;

    // Referência para o BVH
    private BVH bvh;

    void Start() {
        // Coleta todos os colliders dos inimigos
        List<Collider> todosColliders = new List<Collider>();
        foreach (var inimigo in inimigos) {
            todosColliders.AddRange(inimigo.GetColliders());
        }

        // Cria o BVH com os colliders dos inimigos
        bvh = new BVH(todosColliders);
    }

    void Update() {
        // Exemplo de uso: verifica colisões com um raio
        if (Input.GetMouseButtonDown(0)) { // Botão esquerdo do mouse
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;

            if (bvh.Raycast(ray, out hit)) {
                Debug.Log("Acertou: " + hit.collider.name);
            }
        }
    }

    // Desenha os bounding volumes na cena
    private void OnDrawGizmos() {
        if (bvh != null) {
            bvh.root.DrawGizmos();
        }
    }
}