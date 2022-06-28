<div class="carousel-item" data-mdb-interval="1000000000">
    <figure class="figure">
        <div class="controls">
            <span class="badge rounded-pill badge-light border border-success">
              <i class="fas fa-camera-retro" style="color: green"></i> Reference
            </span>
        </div>
        <img id="${id}-reference" src="data:image/png;base64,${reference}"
             class="img-fluid"
             alt="Reference"/>
        <div class="controls">
            <button class="button rounded-pill border border-light" onclick="scaleReference()">
                <i class="fas fa-search" style="color: royalblue"></i>
                Zoom
            </button>
            <button class="button rounded-pill border border-light" onclick="showReferenceIgnoring()">
                <i class="fas fa-adjust" style="color: royalblue"></i>
                Show ignoring
            </button>
        </div>
    </figure>
</div>