import React, { Component } from 'react';
import { Row } from "react-bootstrap";
import './Plan.css'
import CommentSideBar from './Components/CommentSideBar';
import TopButtons from './Components/TopButtons';

class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            listComentarios: [],
            id: "",
            title: ""
        };
        this.handleAddRisk = this.handleAddRisk.bind(this)
    }
    handleAddRisk() {
        // Simple POST request with a JSON body using fetch
        /*const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title: 'React POST Request Example' })
        };
        fetch('https://jsonplaceholder.typicode.com/posts', requestOptions)
            .then(response => response.json())
            .then(data => this.setState({ postId: data.id }));*/
        //alert("xdxd")
        let newList = this.state.listComentarios
        newList.push({ comentario: "Hola mundo", id: 1 })
        this.setState({ listComentarios: newList });
    }
    componentDidMount() {
        // Simple POST request with a JSON body using fetch
        /*const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title: 'React POST Request Example' })
        };
        fetch('https://jsonplaceholder.typicode.com/posts', requestOptions)
            .then(response => response.json())
            .then(data => this.setState({ postId: data.id }));*/
        this.setState({
            listComentarios: [{ comentario: "Hola mundo", id: 1 }, { comentario: "xwdwed", id: 2 }, { comentario: "xdxdxd", id: 3 }, { comentario: "xdxdxd", id: 4 }],
            id: "232424234",
            title: "Plan de calles"
        })
    }

    render() {
        return (
            <div className="Plan-Container">
                {/* Comentarios del Plan */}
                <CommentSideBar />

                {/* Contenedor para el resto de la pagina */}
                <div className="container-fluid Data-Container">

                    {/* Botones de uso en el Plan */}
                    <Row>
                        <TopButtons/>
                    </Row>

                    {/* Datos del Plan */}
                    <Row className="mt-4">
                        <h1>Nombre del plan</h1>
                        <h2>Tipo-ID20211234</h2>     
                        <h4>xx/xx/xxxx</h4>
                        <h4>Ricardo Milos</h4>
                        <p>ontent of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their i</p>
                    </Row>

                    {/* Listas de Datos del Plan */}
                    <Row>
                        {/*{this.state.title}-{"algo"}*/}
                        <div class="card text-center">
                            <div class="card">
                                <ul class="nav nav-pills card-header-pills">
                                    <li class="nav-item">
                                        <a class="nav-link info" href="#">
                                            <button type="button" class="btn btn-outline-secondary">Riesgos</button>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link info" href="#">
                                            <button type="button" class="btn btn-outline-secondary">Insidencia Asociada</button>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link info" href="#">
                                            <button type="button" class="btn btn-outline-secondary">Lista de Involucrados</button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="card-body">
                                sample text
                            </div>
                        </div>
                    </Row>

                </div>
            </div>
        );
    }
}
export default Plan;