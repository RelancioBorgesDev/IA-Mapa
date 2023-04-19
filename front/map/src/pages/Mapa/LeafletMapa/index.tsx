import { useEffect } from "react";
import {
  MapContainer,
  Marker,
  Polyline,
  Popup,
  TileLayer,
} from "react-leaflet";
import { ILocations } from "..";

interface ILeafletMapaProps {
  locais: ILocations[];
  paises: Array<string>;
}

export default function LeafletMapa({
  locais,
  paises,
}: ILeafletMapaProps) {
  const coords = [];
  for (let i = 1; i < locais.length - 1; i++) {
    const { lat, lng } = locais[i];
    coords.push([lat, lng]);
  }

 
  return (
    <MapContainer
      center={[0, -50]}
      zoom={2}
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
          pathOptions={{ color: "blue" }}
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
