let step = 0;

function ensureSvg() {
  let svg = document.getElementById("connectors");
  if (!svg) {
    // Fallback: criar overlay SVG dinamicamente
    const container = document.querySelector(".memory");
    svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("id", "connectors");
    svg.style.position = "absolute";
    svg.style.top = "0";
    svg.style.left = "0";
    svg.style.width = "100%";
    svg.style.height = "100%";
    svg.style.pointerEvents = "none";
    svg.style.zIndex = "10";

    const defs = document.createElementNS("http://www.w3.org/2000/svg", "defs");
    const marker = document.createElementNS("http://www.w3.org/2000/svg", "marker");
    marker.setAttribute("id", "arrow");
    marker.setAttribute("markerWidth", "10");
    marker.setAttribute("markerHeight", "10");
    marker.setAttribute("refX", "10");
    marker.setAttribute("refY", "3");
    marker.setAttribute("orient", "auto");
    marker.setAttribute("viewBox", "0 0 10 10");
    const path = document.createElementNS("http://www.w3.org/2000/svg", "path");
    path.setAttribute("d", "M0,0 L10,3 L0,6");
    path.setAttribute("fill", "#555");
    marker.appendChild(path);
    defs.appendChild(marker);
    svg.appendChild(defs);
    container.appendChild(svg);
  }
  return svg;
}

function getCenters(fromEl, toEl, containerRect) {
  const f = fromEl.getBoundingClientRect();
  const t = toEl.getBoundingClientRect();
  const x1 = f.right - containerRect.left;
  const y1 = f.top + f.height / 2 - containerRect.top;
  const x2 = t.left - containerRect.left;
  const y2 = t.top + t.height / 2 - containerRect.top;
  return { x1, y1, x2, y2 };
}

function drawConnector(fromId, toId, id, opts = {}) {
  const svg = ensureSvg();
  const container = document.querySelector(".memory");
  const containerRect = container.getBoundingClientRect();
  const w = Math.round(containerRect.width);
  const h = Math.round(containerRect.height);
  svg.setAttribute("width", w);
  svg.setAttribute("height", h);
  svg.setAttribute("viewBox", `0 0 ${w} ${h}`);

  const fromEl = document.getElementById(fromId);
  const toEl = document.getElementById(toId);
  if (!fromEl || !toEl) return;
  if (fromEl.classList.contains("hidden") || toEl.classList.contains("hidden")) {
    removeConnector(id);
    return;
  }

  let line = document.getElementById(id);
  const { x1, y1, x2, y2 } = getCenters(fromEl, toEl, containerRect);
  if (!line) {
    line = document.createElementNS("http://www.w3.org/2000/svg", "line");
    line.setAttribute("id", id);
    line.classList.add("connector");
    // garantir a ponta da seta, independente de CSS
    line.setAttribute("marker-end", "url(#arrow)");
    if (opts.reassign) {
      line.classList.add("reassign");
    }
    svg.appendChild(line);
  }
  line.setAttribute("x1", x1);
  line.setAttribute("y1", y1);
  line.setAttribute("x2", x2);
  line.setAttribute("y2", y2);
}

function removeConnector(id) {
  const line = document.getElementById(id);
  if (line) line.remove();
}

function updateAllConnectors() {
  // a -> obj1
  drawConnector("a", "obj1", "conn-a-obj1");
  // b -> obj1
  drawConnector("b", "obj1", "conn-b-obj1");
  // c -> obj2
  drawConnector("c", "obj2", "conn-c-obj2");
  // p pode apontar para obj1 ou obj3 conforme o passo
  if (step <= 8) {
    drawConnector("p", "obj1", "conn-p-obj1");
    removeConnector("conn-p-obj3");
  } else {
    removeConnector("conn-p-obj1");
    drawConnector("p", "obj3", "conn-p-obj3", { reassign: true });
  }
}

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
      info.innerText = "ajustarPreco(a): p aponta para o MESMO objeto de a (Obj1).";
      break;

    case 7:
      // p altera o preço do mesmo objeto referenciado por a/b
      document.getElementById("preco1").innerText = "5.5";
      info.innerText = "p.setPreco(5.5) no MESMO objeto de a/b. Obj1 atualiza para 5.5.";
      const obj1 = document.getElementById("obj1");
      obj1.classList.remove("flash");
      void obj1.offsetWidth;
      obj1.classList.add("flash");
      break;

    case 8:
      document.getElementById("obj3").classList.remove("hidden");
      info.innerText = "Reatribuição local: p → Uva(8.0). a/b continuam em Banana(5.5).";
      break;

    default:
      info.innerText = "Fim da animação 🎉";
  }

  step++;
  updateAllConnectors();
}

window.addEventListener("resize", updateAllConnectors);
document.addEventListener("DOMContentLoaded", updateAllConnectors);
