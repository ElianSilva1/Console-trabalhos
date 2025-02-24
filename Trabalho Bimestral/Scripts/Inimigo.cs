using System.Collections.Generic;
using UnityEngine;

public class Inimigo : MonoBehaviour {
    // Referências para os colliders das partes do corpo
    public Collider cabecaCollider;
    public Collider bracoCollider;
    public Collider pernaCollider;

    // Lista de colliders do inimigo
    public List<Collider> GetColliders() {
        return new List<Collider> { cabecaCollider, bracoCollider, pernaCollider };
    }
}