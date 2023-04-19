import { useState, useEffect, useContext } from "react";

import style from "./style.module.css";
import {
  coordenadasEuropeu,
  coordenadasAfrica,
  coordenadasAmericano,
  ICoordenadas,
} from "../../utils/coordenadas";
import { FormDataContext } from "../../contexts/FormDataContext";
import FormularioPesqusia from "./FormPesquisa";
import { PesquisaDadosContext } from "../../contexts/PesquisaDadosContext";
import { api } from "../../api/api";
import LeafletMapa from "./LeafletMapa";
import { toast, ToastContainer } from "react-toastify";
import { Link } from "react-router-dom";

export interface ILocations {
  lat: number;
  lng: number;
}

export default function Mapa() {
  const { dadosForm } = useContext(FormDataContext);
  const { pesquisaDados, setPesquisaDados } = useContext(PesquisaDadosContext);
  const [locais, setLocais] = useState<ILocations[]>([]);
  const [paises, setPaises] = useState<Array<string>>([]);
  const [primeiraRequisicao, setPrimeiraRequisicao] = useState(false);

  async function recebePaises() {
    limparVetor();
    let resposta;
    const { algorithm, continente } = dadosForm;
    const { destino, partida, limiteMaximo } = pesquisaDados;
    if (
      dadosForm.algorithm == "profundidadelimitada" ||
      dadosForm.algorithm == "aprofundamentoiterativo"
    ) {
      resposta = await api.get(
        `/${algorithm
          .toLowerCase()
          .trim()}/${continente.toLowerCase()}/${partida}/${destino}/${limiteMaximo}`
      );
    } else {
      resposta = await api.get(
        `/${algorithm.toLowerCase()}/${continente.toLowerCase()}/${partida}/${destino}`
      );
    }
    setPaises(resposta.data);
    console.log(paises);
    setPrimeiraRequisicao(true);
  }

  function verificaContinenteRetornaObjetoCoordenadas(): ICoordenadas {
    const { continente } = dadosForm;
    switch (continente.toLowerCase()) {
      case "americano":
        return coordenadasAmericano;
      case "europeu":
        return coordenadasEuropeu;
      case "africano":
        return coordenadasAfrica;
      default:
        return {};
    }
  }

  function verificaPaisesERetornaCoordenadas(paises: Array<string>) {
    const objCoordenada = verificaContinenteRetornaObjetoCoordenadas();
    paises.map((pais: string) => {
      const coordenadas = objCoordenada[pais];
      if (coordenadas) {
        const lat: number = coordenadas.lat;
        const lng: number = coordenadas.lng;
        setLocais((prevLocais) => [...prevLocais, { lat, lng }]);
      }
    });
  }

  function limparVetor() {
    setPaises([]);
    setLocais([]);
    setPrimeiraRequisicao(false);
  }

  useEffect(() => {
    if (pesquisaDados.destino && pesquisaDados.partida) {
      recebePaises();
    }
  }, [pesquisaDados]);

  useEffect(() => {
    verificaPaisesERetornaCoordenadas(paises);
  }, [paises]);

  useEffect(() => {
    if (paises[0] === "Caminho não encontrado" && primeiraRequisicao) {
      toast.error("Caminho não encontrado");
    } else if (primeiraRequisicao && paises.length > 0) {
      toast.success("Caminho encontrado");
    }
  }, [paises, primeiraRequisicao]);

  return (
    <div className={style.map_container}>
      <FormularioPesqusia setPesquisaDados={setPesquisaDados} />
      <LeafletMapa locais={locais} paises={paises} />
      <ToastContainer
        position='top-right'
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme='colored'
        style={{
          zIndex: 2000000000,
          color: "red",
        }}
      />
      <Link to='/'>
        <button className={style.btn_voltar}>{"<-"} Voltar</button>
      </Link>

      {dadosForm.continente && (
        <span className={style.exibe_continente}>
          Continente {dadosForm.continente}
        </span>
      )}
      {dadosForm.algorithm && (
        <span className={style.exibe_algoritimo}>
          Método {dadosForm.algorithm}
        </span>
      )}
    </div>
  );
}
