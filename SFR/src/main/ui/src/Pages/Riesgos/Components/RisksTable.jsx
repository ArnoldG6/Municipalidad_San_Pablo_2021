import React, { Component } from 'react';
import './RisksTable.css';
import { Table, Button } from "react-bootstrap";
//import { Link } from 'react-router-dom';

class RisksTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sortingWay: 'desc',
            value: 'date'
        };

    }

    handleSort(parameter) {
        let sort = this.state.sortingWay;
        if (parameter === this.state.value) {
            if (sort === 'desc') {
                sort = 'asc';
            } else {
                sort = 'desc';
            }
        }
        else {
            sort = 'desc';
        }
        this.setState(
            {
                sortingWay: sort,
                value: parameter
            }, () => {
                this.props.updateRiesgosSort(parameter, sort);
            }
        );
    }

    render() {
        return (
            <Table hover>
                <thead>
                    <tr>
                        <th><button className='header-button' id="idButton" onClick={() => { this.handleSort('pk_id') }}>ID</button></th>
                        <th><button className='header-button' id="nameButton" onClick={() => { this.handleSort('name') }}> Nombre</button></th>
                        <th><button className='header-button' id="idButton" onClick={() => { this.handleSort('generalType') }}>Tipo General</button></th>
                        <th><button className='header-button' id="idButton" onClick={() => { this.handleSort('areaType') }}>Tipo por Área</button></th>
                        <th><button className='header-button' id="idButton" onClick={() => { this.handleSort('specType') }}>Tipo Específico</button></th>
                        <th><button className='header-button' id="idButton" onClick={() => { this.handleSort('probability') }}>Probabilidad</button></th>
                        <th><button className='header-button' id="stateButton" onClick={() => { this.handleSort('impact') }}>Impacto</button></th>
                        <th><button className='header-button' id="typeButton" onClick={() => { this.handleSort('magnitude') }}>Magnitud</button></th>
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
                                        <td className="nameSlot">{risk.name}</td>
                                        <td>{risk.generalType}</td>
                                        <td>{risk.areaType}</td>
                                        <td>{risk.specType}</td>
                                        <td>{risk.probability}</td>
                                        <td>{risk.impact}</td>
                                        <td>{risk.magnitude}</td>
                                        <td><Button>Editar</Button></td>
                                        <td><Button
                                            onClick={() => { this.props.openModalDelete(risk.id) }}
                                            disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>Eliminar</Button></td>
                                    </tr>
                                )
                            })}
                        </tbody>
                }
            </Table >
        );
    }
};
export default RisksTable;