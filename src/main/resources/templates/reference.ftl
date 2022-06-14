<div class="carousel-item" data-mdb-interval="1000000000">
    <figure class="figure">
        <h4>
            <button class="badge rounded-pill badge-light" onclick="scaleReference()">Zoom</button>
        </h4>
        <img id="${id}-reference" src="data:image/png;base64,${reference}"
             class="img-fluid"
             alt="Reference"/>
        <figcaption class="figure-caption">
            <h4>
                <button class="badge rounded-pill badge-success" disabled>
                    <i class="fas fa-camera-retro"></i> Reference
                </button>
            </h4>
        </figcaption>
    </figure>
</div>