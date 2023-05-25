import { useEffect } from "react";
import {
  MapContainer,
  Marker,
  Polyline,
  Popup,
  TileLayer,
} from "react-leaflet";
import { ILocations } from "../../index";

interface ILeafletMapaProps {
  locais: ILocations[];
  paises: Array<string>;
}

export default function LeafletMapa({ locais, paises }: ILeafletMapaProps) {
  return (
    <MapContainer
      center={[0, -50]}
      zoom={3}
      style={{ height: "100%", width: "100%" }}
      preferCanvas={true}
    >
      <TileLayer
        url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
        attribution='&copy; OpenStreetMap contributors'
        maxZoom={18}
      />
      {locais.length > 0 && (
        <Polyline
          pathOptions={{ color: "green" }}
          positions={locais.map((locais) => [locais.lat, locais.lng])}
        />
      )}

      {locais.length > 0 &&
        locais.map((local, index) => {
          const isInicio = index === 0;
          const isDestino = index === locais.length - 1;
          const position = [local.lat, local.lng];

          return isInicio || isDestino ? (
            <Marker key={index} position={position}>
              <Popup>{paises[index]}</Popup>
            </Marker>
          ) : null;
        })}
    </MapContainer>
  );
}
