import React, { Component } from 'react';
import './RisksTable.css';
import { Table, Button, Accordion } from "react-bootstrap";
import { Link } from 'react-router-dom';

class RisksTable extends Component {
    render() {
        return (
            <div className='mt-2'>
                {/* Mobile */}
                <div className='d-lg-none'>
                    <Accordion>
                        {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                            this.props.riesgos.length === 0 ? <h1>No se han agregado riesgos</h1> :
                                this.props.riesgos.map((risk) => {
                                    return (
                                        <Accordion.Item eventKey={risk.id}>
                                            <Accordion.Header >
                                                {risk.name}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID: {risk.id} <br />
                                                    Tipo General: {risk.generalType} <br />
                                                    Tipo por Área: {risk.areaType} <br />
                                                    Tipo Específico: {risk.specType} <br />
                                                    Probabilidad: {risk.probability} <br />
                                                    Impacto: {risk.impact} <br />
                                                    Magnitud: {risk.magnitude} <br />
                                                </p>
                                                <Link to={{ pathname: "/riesgo", search: `?id=${risk.id}` }}>+ Más información</Link>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    );
                                })
                        }
                    </Accordion>
                </div>
                {/* PC */}
                <div className='d-none d-lg-block'>
                    <Table hover>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Tipo General</th>
                                <th>Tipo por Área</th>
                                <th>Tipo Específico</th>
                                <th>Probabilidad</th>
                                <th>Impacto</th>
                                <th>Magnitud</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>

                        {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                            this.props.riesgos.length === 0 ? <h1>No se han agregado riesgos</h1> :
                                <tbody>
                                    {this.props.riesgos.map((risk) => {
                                        return (
                                            <tr key={risk.id}>
                                                <td>{risk.id}</td>
                                                <td className="nameSlot"><Link to={{ pathname: "/riesgo", search: `?id=${risk.id}` }}><p>{risk.name}</p></Link></td>
                                                <td>{risk.generalType}</td>
                                                <td>{risk.areaType}</td>
                                                <td>{risk.specType}</td>
                                                <td>{risk.probability}</td>
                                                <td>{risk.impact}</td>
                                                <td>{risk.magnitude}</td>
                                                <td><Button onClick={() => { this.props.openModalEdit(risk.id) }} >Editar</Button></td>
                                                <td><Button
                                                    onClick={() => { this.props.openModalDelete(risk.pkID) }}
                                                    disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>Eliminar</Button></td>
                                            </tr>
                                        )
                                    })}
                                </tbody>
                        }
                    </Table >
                </div>
            </div>
        );
    }
};
export default RisksTable;