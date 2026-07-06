# CountryTags Plugin

## Minecraft Plugin for 1.21.11+.

_(in development!)_

CountryTags detecta el país de cada jugador a partir de su IP y expone esa información como un tag de chat coloreado, listo para usarse a través de PlaceholderAPI en formato texto o de cabeza (`<head:...>`).

## Cómo funciona

1. Al entrar al servidor, el plugin toma la IP del jugador y consulta una API de geolocalización configurable para obtener el `countryCode` (ISO alpha-2).
2. Ese código se resuelve contra un catálogo interno de países (`Country`), que trae para cada uno su nombre, código, un tag coloreado en formato MiniMessage (ej. `<#51B9D4>#ᴀ<#C1A427>ʀ<#51B9D4>ɢ` para Argentina) y la URL de la textura de su bandera para mostrarla como cabeza.
3. El jugador puede elegir o cambiar su país manualmente en cualquier momento con `/countrytag`, que abre un diálogo nativo de Minecraft con la lista completa de países.
4. El tag resultante se expone mediante PlaceholderAPI para usarlo en el chat, tablist, scoreboards, etc.

## Comandos

| Comando | Descripción |
|---|---|
| `/countrytag` | Abre un menú de diálogo para seleccionar o cambiar el país mostrado. |

## Placeholders (PlaceholderAPI)

| Placeholder | Descripción |
|---|---|
| `%countrytags_tag%` | Devuelve el tag del país (o el tag personalizado del jugador) como texto normal. |
| `%countrytags_head%` | Devuelve el tag en formato `<head:...>` para mostrar la cabeza/bandera del país. |

Ambos placeholders respetan el permiso configurado: si el jugador no lo tiene, devuelven una cadena vacía.

## Configuración (`config.yml`)

```yaml
api:
  # Endpoint GET usado para resolver el país desde la IP del jugador.
  # {ip} se reemplaza por la IP del jugador.
  url: "https://free.freeipapi.com/api/json/{ip}"

permission:
  # Si está en true, el tag solo se muestra a jugadores con el permiso indicado.
  enabled: true
  node: "countrytags.tag.show"
```

## Permisos

| Permiso | Descripción | Default |
|---|---|---|
| `countrytags.tag.show` | Permite que se muestre el tag de país del jugador. | `true` |

## Requisitos

- Servidor Paper (o derivado) 1.21.11+.
- PlaceholderAPI (opcional, requerido solo para usar los placeholders).
