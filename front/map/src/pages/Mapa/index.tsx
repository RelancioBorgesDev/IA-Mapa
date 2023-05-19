import { useState, useEffect, useContext } from "react";

import style from "./style.module.css";
import { FormDataContext } from "../../contexts/FormDataContext";
import FormularioPesqusia from "./components/FormPesquisa";
import { PesquisaDadosContext } from "../../contexts/PesquisaDadosContext";
import { apiAlgoritimos } from "../../api/api";
import LeafletMapa from "./components/LeafletMapa";
import { toast, ToastContainer } from "react-toastify";
import { Link } from "react-router-dom";
import { PaisesContext } from "../../contexts/PaisesContext";

export interface ILocations {
  lat: number;
  lng: number;
}

export default function Mapa() {
  const { dadosForm } = useContext(FormDataContext);
  const { pesquisaDados, setPesquisaDados } = useContext(PesquisaDadosContext);
  const { paisesCoordenadas } = useContext(PaisesContext);

  const [locais, setLocais] = useState<ILocations[]>([]);
  const [paises, setPaises] = useState<Array<string>>([]);
  const [primeiraRequisicao, setPrimeiraRequisicao] = useState(false);
  const [formEnviado, setFormEnviado] = useState(false);

  async function recebePaises() {
    limparVetor();
    let resposta;
    const { algorithm, continente } = dadosForm;
    const { destino, partida, limiteMaximo } = pesquisaDados;
    if (
      dadosForm.algorithm == "profundidadelimitada" ||
      dadosForm.algorithm == "aprofundamentoiterativo"
    ) {
      resposta = await apiAlgoritimos.get(
        `/${algorithm
          .toLowerCase()
          .trim()}/${continente.toLowerCase()}/${partida}/${destino}/${limiteMaximo}`
      );
    } else {
      resposta = await apiAlgoritimos.get(
        `/${algorithm.toLowerCase()}/${continente.toLowerCase()}/${partida}/${destino}`
      );
    }
    setPaises(resposta.data);
    setPrimeiraRequisicao(true);
  }

  function verificaPaisesERetornaCoordenadas(paises: Array<string>) {
    paises.map((pais: string) => {
      const coordenadas = paisesCoordenadas[pais];
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
    if (formEnviado && pesquisaDados.destino && pesquisaDados.partida) {
      recebePaises();
    }
  }, [formEnviado, pesquisaDados]);

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
      <FormularioPesqusia
        setPesquisaDados={setPesquisaDados}
        setFormEnviado={setFormEnviado}
      />
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
