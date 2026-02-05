let step = 0;

function nextStep() {
  const info = document.getElementById("info");

  switch (step) {

    case 0:
      document.getElementById("a").classList.remove("hidden");
      document.getElementById("obj1").classList.remove("hidden");
      info.innerText = "Produto a criado. Objeto Banana(5.0) alocado na Heap.";
      break;

    case 1:
      document.getElementById("b").classList.remove("hidden");
      info.innerText = "Produto b = a. a e b apontam para o MESMO objeto.";
      break;

    case 2:
      info.innerText = "a == b → true (mesma referência).";
      break;

    case 3:
      document.getElementById("preco1").innerText = "6.0";
      info.innerText = "b.setPreco(6.0). O objeto muda e a também enxerga 6.0.";
      break;

    case 4:
      document.getElementById("c").classList.remove("hidden");
      document.getElementById("obj2").classList.remove("hidden");
      info.innerText = "Produto c criado. Novo objeto Banana(5.0), referência diferente.";
      break;

    case 5:
      info.innerText = "a == c → false. a.equals(c) depende da implementação.";
      break;

    case 6:
      document.getElementById("p").classList.remove("hidden");
      info.innerText = "ajustarPreco(a): p recebe uma CÓPIA da referência de a.";
      break;

    case 7:
      document.getElementById("obj3").classList.remove("hidden");
      info.innerText = "p = new Produto(Uva, 8.0). Apenas p muda. a continua apontando para Banana.";
      break;

    default:
      info.innerText = "Fim da animação 🎉";
  }

  step++;
}
