import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Mapa from "./pages/Mapa";

export default function Router() {
  return (
    <Routes>
      <Route path='/' element={<Home />} />
      <Route path='/mapa' element={<Mapa />} />
    </Routes>
  );
}
