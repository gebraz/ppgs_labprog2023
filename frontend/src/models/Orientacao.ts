export interface Orientador {
  id: string;
  lattes: string;
  nome: string;
  dataAtualizacao: string;
}

export interface Orientacao {
  id: number;
  tipo: string;
  natureza: string;
  discente: string;
  titulo: string;
  ano: number;
  modalidade: string;
  instituicao: string;
  curso: string;
  status: string;
  orientador: Orientador;
}
