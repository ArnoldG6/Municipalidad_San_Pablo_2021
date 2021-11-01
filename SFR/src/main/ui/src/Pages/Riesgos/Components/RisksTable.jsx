import React, { Component } from 'react';
import './RisksTable.css';
import { Table } from "react-bootstrap";
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


                    </tr>
                </thead>
                <tbody>



                    {this.props.riesgos.map((risk) => {
                        /*
                        let statusClass = "";
                        switch (plan.status) {
                            case 'Activo':
                                statusClass = 'in-progress bubble';
                                break;
                            case 'Inactivo':
                                statusClass = 'no-progress bubble';
                                break;
                            case 'Completo':
                                statusClass = 'completed bubble';
                                break;
                            default:
                                statusClass = 'unknown bubble';
                                break;
                        }*/
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
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
        );
    }
};
export default RisksTable;