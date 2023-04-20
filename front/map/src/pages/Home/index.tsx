import { useContext } from "react";
import { Link } from "react-router-dom";
import { FormDataContext } from "../../contexts/FormDataContext";
import style from "./style.module.css";
import { FaGlobeEurope, FaGlobeAfrica, FaGlobeAmericas } from "react-icons/fa";
export default function Home() {
  const { dadosForm, setDadosForm } = useContext(FormDataContext);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setDadosForm({ ...dadosForm, [name]: value });
  };

  return (
    <main className={style.main}>
      <form className={style.formulario}>
        <h1>Selecione um continente</h1>
        <fieldset className={style.main_inputs_countries}>
          <div className={style.input_container}>
            <input
              type='radio'
              name='continente'
              id='americano'
              value='Americano'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='americano'>Americano</label>
              <FaGlobeAmericas size={32}/>
            </div>
          </div>
          <div className={style.input_container}>
            <input
              type='radio'
              name='continente'
              id='europeu'
              value='Europeu'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='europeu'>Europeu</label>
              <FaGlobeEurope size={32}/>
            </div>
          </div>
          <div className={style.input_container}>
            <input
              type='radio'
              name='continente'
              id='africano'
              value='Africano'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='africano'>Africano</label>
              <FaGlobeAfrica size={32}/>
            </div>
          </div>
        </fieldset>
        <h1>Selecione um algoritimo de busca</h1>
        <div className={style.main_inputs_search_algorithms}>
          <div className={style.input_container}>
            <input
              type='radio'
              name='algorithm'
              id='amplitude'
              value='Amplitude'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='amplitude'>Amplitude</label>
            </div>
          </div>

          <div className={style.input_container}>
            <input
              type='radio'
              name='algorithm'
              id='profundidade'
              value='Profundidade'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='profundidade'>Profundidade</label>
            </div>
          </div>

          <div className={style.input_container}>
            <input
              type='radio'
              name='algorithm'
              id='profundidade_limitada'
              value='profundidadelimitada'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='profundidade_limitada'>
                Profundidade Limitada
              </label>
            </div>
          </div>

          <div className={style.input_container}>
            <input
              type='radio'
              name='algorithm'
              id='aprofundamento_iterativo'
              value='aprofundamentoiterativo'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='aprofundamento_iterativo'>
                Aprofundamento Iterativo
              </label>
            </div>
          </div>

          <div className={style.input_container}>
            <input
              type='radio'
              name='algorithm'
              id='bidirecional'
              value='Bidirecional'
              onChange={handleChange}
            />
            <div className={style.radio_info}>
              <label htmlFor='bidirecional'>Bidirecional</label>
            </div>
          </div>
        </div>

        <Link className={style.btn_link} to='/mapa'>
          <button type='submit' className={style.btn_enviar}>
            Enviar
          </button>
        </Link>
      </form>
    </main>
  );
}
