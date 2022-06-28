<div class="carousel-item" data-mdb-interval="1000000000">
    <figure class="figure">
        <div class="controls">
            <span class="badge rounded-pill badge-light border border-warning">
              <i class="fas fa-camera-retro" style="color: orange"></i> Diff ${diffSize}px
            </span>
        </div>
        <img id="${id}-diff" src="data:image/png;base64,${diff}"
             class="img-fluid"
             alt="Diff"/>
        <div class="controls">
            <button class="button rounded-pill border border-light" onclick="scaleDiff()">
                <i class="fas fa-search" style="color: royalblue"></i>
                Zoom
            </button>
            <button class="button rounded-pill border border-light" onclick="showDiffIgnoring()">
                <i class="fas fa-adjust" style="color: royalblue"></i>
                Show ignoring
            </button>
        </div>
    </figure>
</div>