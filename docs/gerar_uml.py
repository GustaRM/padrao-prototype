"""Gera o diagrama UML (estilo ferramenta de desenho, fundo quadriculado)."""
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle, FancyArrowPatch

LH, PAD, NAME_H = 0.52, 0.25, 0.75

def novo(W_UN, H_UN, w_in):
    global ax, fig
    fig, ax = plt.subplots(figsize=(w_in, w_in * H_UN / W_UN))
    ax.set_xlim(0, W_UN); ax.set_ylim(0, H_UN); ax.set_aspect("equal"); ax.axis("off")
    for i in range(W_UN + 1):
        ax.plot([i, i], [0, H_UN], color="#E3E3E3", lw=0.6, zorder=0)
    for j in range(H_UN + 1):
        ax.plot([0, W_UN], [j, j], color="#E3E3E3", lw=0.6, zorder=0)

def box(cx, top, w, name, stereo=None, attrs=(), methods=()):
    h_name = NAME_H * (2 if stereo else 1) + (0.15 if stereo else 0)
    h_attr = max(len(attrs), 1) * LH + PAD
    h_meth = max(len(methods), 1) * LH + PAD
    h = h_name + h_attr + h_meth
    x0, y0 = cx - w / 2, top - h
    ax.add_patch(Rectangle((x0, y0), w, h, fc="white", ec="black", lw=1.4, zorder=2))
    ax.plot([x0, x0 + w], [top - h_name] * 2, color="black", lw=1.2, zorder=3)
    ax.plot([x0, x0 + w], [top - h_name - h_attr] * 2, color="black", lw=1.2, zorder=3)
    ty = top - 0.45
    if stereo:
        ax.text(cx, ty, f"«{stereo}»", ha="center", va="center", fontsize=9, zorder=4)
        ty -= 0.65
    ax.text(cx, ty, name, ha="center", va="center", fontsize=11, fontweight="bold", zorder=4)
    for k, a in enumerate(attrs):
        ax.text(x0 + 0.25, top - h_name - PAD / 2 - LH * (k + .5), a, ha="left", va="center", fontsize=8.5, zorder=4)
    for k, m in enumerate(methods):
        ax.text(x0 + 0.25, top - h_name - h_attr - PAD / 2 - LH * (k + .5), m, ha="left", va="center", fontsize=8.5, zorder=4)
    return dict(cx=cx, cy=(top + y0) / 2, top=top, bottom=y0, left=x0, right=x0 + w)

def line(pts, dashed=False):
    xs, ys = zip(*pts)
    ax.plot(xs, ys, color="black", lw=1.3, zorder=1, linestyle=(0, (5, 4)) if dashed else "-")

def arrow(p0, p1, style, dashed=False):
    a = FancyArrowPatch(p0, p1, arrowstyle=style, mutation_scale=22 if "|" in style else 18, lw=1.3,
                        color="black", zorder=1, shrinkA=0, shrinkB=0,
                        linestyle=(0, (5, 4)) if dashed else "-")
    if "|" in style:
        a.set_facecolor("white")
    ax.add_patch(a)

def legenda(x, y, titulo, itens):
    ax.text(x, y, titulo, fontsize=9.5, fontweight="bold")
    for k, t in enumerate(itens):
        ax.text(x, y - 0.9 - k * 0.8, f"• {t}", fontsize=8.5)

def salvar(nome="diagrama-uml.png"):
    plt.savefig(nome, dpi=140, bbox_inches="tight", facecolor="white")
novo(74, 32, 25)
cli = box(8, 30, 12, "Main", None, [], ["+ main(args)"])
cat = box(36, 30, 32, "CatalogoContratos", "Registro de protótipos", ["- prototipos: Map<String, ContratoTrabalho>"],
          ["+ registrar(chave, modelo: ContratoTrabalho)", "+ obter(chave): ContratoTrabalho", "+ quantidadeModelos(): int", "+ padrao(): CatalogoContratos {static}"])
clo = box(64, 30, 16, "Cloneable", "interface", [], ["+ clone(): Object"])
con = box(28, 20, 32, "ContratoTrabalho", "Prototype",
          ["- empregado: String", "- cargo: String", "- salario: double", "- cargaHorariaSemanal: int", "- localTrabalho: Endereco", "- clausulas: List<String>"],
          ["+ clone(): ContratoTrabalho", "+ adicionarClausula(c): ContratoTrabalho", "+ getLocalTrabalho(): Endereco", "+ getClausulas(): List<String>",
           "+ get/set(empregado, cargo, salario, ...)", "+ resumo(): String"])
end = box(64, 19, 18, "Endereco", None, ["- logradouro: String", "- cidade: String", "- uf: String"],
          ["+ clone(): Endereco", "+ get/set(...)", "+ toString(): String"])
arrow((cli["right"], 28.5), (cat["left"], 28.5), "->", True)
arrow((30, cat["bottom"]), (30, con["top"]), "->")
ax.text(30.4, (cat["bottom"] + con["top"]) / 2, "prototipos 0..*", fontsize=9)
bus = 23.6
line([(con["right"], 18.8), (49, 18.8), (49, bus), (64, bus)], True)
line([(64, end["top"]), (64, bus)], True)
arrow((64, bus), (64, clo["bottom"]), "-|>", True)
arrow((con["right"], 16), (end["left"], 16), "->")
ax.text(con["right"] + 0.4, 16.4, "localTrabalho 1", fontsize=9)
legenda(48, 10.5, "Legenda", ["ContratoTrabalho = Prototype (sabe se clonar)", "clone() faz cópia profunda (Endereco e cláusulas)",
                              "CatalogoContratos devolve sempre uma cópia", "Realização: tracejada + triângulo vazado"])
salvar("diagrama-uml.png")
