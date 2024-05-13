import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FormDataContext } from "../../contexts/FormDataContext";
import { FaGlobeEurope, FaGlobeAfrica, FaGlobeAmericas } from "react-icons/fa";
import { apiMetodos } from "../../api/api";
import style from "./style.module.css";

interface IMetodos {
  label: string;
}

export default function Home() {
  const { dadosForm, setDadosForm } = useContext(FormDataContext);
  const [metodos, setMetodos] = useState<Array<IMetodos>>([]);
  const [isCamposVazios, setIsCamposVazios] = useState(false);

  async function recebeMetodos() {
    const respostaMetodos = await apiMetodos.get("listaMetodosNomes");
    setMetodos(respostaMetodos.data);

    if (!dadosForm.continente || !dadosForm.algorithm) {
      setIsCamposVazios(true);
    }
  }

  useEffect(() => {
    recebeMetodos();
  }, []);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setDadosForm({ ...dadosForm, [name]: value });
    setIsCamposVazios(false);
  };

  return (
    <main className={style.main}>
      <form className={style.formulario}>
        <h1>Selecione um continente</h1>
        <fieldset className={style.main_inputs_countries}>
          <div className={style.input_container}>
            <input
              type="radio"
              name="continente"
              id="americano"
              value="Americano"
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor="americano">Americano</label>
              <FaGlobeAmericas size={32} />
            </div>
          </div>
          <div className={style.input_container}>
            <input
              type="radio"
              name="continente"
              id="europeu"
              value="Europeu"
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor="europeu">Europeu</label>
              <FaGlobeEurope size={32} />
            </div>
          </div>
          <div className={style.input_container}>
            <input
              type="radio"
              name="continente"
              id="africano"
              value="Africano"
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor="africano">Africano</label>
              <FaGlobeAfrica size={32} />
            </div>
          </div>
        </fieldset>
        <h1>Selecione um algoritimo de busca</h1>
        <div className={style.main_inputs_search_algorithms}>
          {metodos.map(({ label }, index) => (
            <div key={index} className={style.input_container}>
              <input
                type="radio"
                name="algorithm"
                id="amplitude"
                value={label.toLowerCase()}
                onChange={handleChange}
              />
              <div className={style.radio_info}>
                <label htmlFor="amplitude">{label}</label>
              </div>
            </div>
          ))}
        </div>

        <Link className={style.btn_link} to="/mapa">
          <button
            type="submit"
            className={style.btn_enviar}
            disabled={isCamposVazios}
          >
            Enviar
          </button>
        </Link>
      </form>
    </main>
  );
}
